/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.service;

import com.sanjnan.server.enums.Role;
import com.sanjnan.server.dao.*;
import com.sanjnan.server.entity.AccountEntity;
import com.sanjnan.server.entity.ComputeRegionEntity;
import com.sanjnan.server.entity.RoleEntity;
import com.sanjnan.server.entity.TenantEntity;
import com.sanjnan.server.pojos.constants.H2OConstants;
import com.sanjnan.server.utils.SanjnanPasswordEncryptionManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vinay on 1/8/16.
 */
@Service
public class SetupService {

  private static final Logger logger = Logger.getLogger(SetupService.class);
  @Autowired
  private ComputeRegionDao computeRegionDao;
  @Autowired
  private TenantDao tenantDao;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private RoleDao roleDao;
  @Autowired
  private DeviceDao deviceDao;

  @Transactional
  public String setup() throws NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidParameterSpecException {
    return checkAndInitializeSeedData();
  }
  
  @Transactional
  public String unsetup() throws NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException, 
      IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidParameterSpecException {
    return deleteSeedData();
  }
  
  private String checkAndInitializeSeedData() throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException, InvalidKeySpecException {

    StringBuffer sb = new StringBuffer();
    Set<ComputeRegionEntity> computeRegions = new HashSet<>();
    if (computeRegionDao.count() == 0) {

      logger.log(Level.INFO, "No compute regions found.. Creating...");
      sb.append("No compute regions found.. Creating...\n");
      ComputeRegionEntity computeRegionEntity = new ComputeRegionEntity(H2OConstants.H2O_DEFAULT_COMPUTE_REGION_NAME,
              H2OConstants.H2O_DEFAULT_API_URL);
      computeRegionDao.save(computeRegionEntity);
      computeRegions.add(computeRegionEntity);
    }
    List<TenantEntity> tenantEntityList = tenantDao.findByDiscriminator(H2OConstants.H2O_INTERNAL_TENANT);
    TenantEntity te;
    if (tenantEntityList == null || tenantEntityList.size() == 0) {

      sb.append("No default tenant found.. Creating...\n");
      logger.log(Level.INFO, "No default tenant found.. Creating...");
      te = new TenantEntity(H2OConstants.H2O_INTERNAL_TENANT,
              H2OConstants.H2O_INTERNAL_ADMIN_EMAIL,
              H2OConstants.H2O_INTERNAL_TENANT,
          computeRegions);
      tenantDao.save(te);
    }
    else {
      te = tenantEntityList.get(0);
    }
    List<AccountEntity> accountEntityList = accountDao.findByTenant(te);
    if (accountEntityList == null || accountEntityList.size() == 0) {
      Set<RoleEntity> roles = roleDao.findByRole(Role.ADMIN.getValue());
      if (roles == null || roles.size() == 0) {
        RoleEntity role = new RoleEntity(Role.ADMIN.getValue());
        roleDao.save(role);
        roles.add(role);
      }
      sb.append("No default user found for tenant "
          + te.getName() + " with uuid " + te.getId() + ". Creating...\n");
      logger.log(Level.INFO, "No default user found for tenant "
          + te.getName() + " with uuid " + te.getId()
          + ". Creating...");
      AccountEntity account = new AccountEntity(te,
              H2OConstants.H2O_INTERNAL_DEFAULT_USER,
              H2OConstants.H2O_INTERNAL_ADMIN_EMAIL,
              SanjnanPasswordEncryptionManager.INSTANCE.encrypt(te.getName(), H2OConstants.H2O_INTERNAL_DEFAULT_USER, H2OConstants.H2O_INTERNAL_DEFAULT_PASSWORD),
          new HashSet<String>(),
          roles,
          te.getNextComputeRegionEntity());
      accountDao.save(account);
    }
    logger.log(Level.INFO, "Initialization of seed data done..");
    sb.append("Initialization of see data done..\n");
    return "{ \"message\" : \"" + sb.toString() + "\"}";
  }
  private String deleteSeedData() throws NoSuchPaddingException, UnsupportedEncodingException, 
      IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException, InvalidKeySpecException {
    
    StringBuffer sb = new StringBuffer();
    accountDao.deleteAll();
    sb.append("Deleting accounts.");
    deviceDao.deleteAll();
    sb.append("Deleting devices.");
    roleDao.deleteAll();
    tenantDao.deleteAll();
    sb.append("Deleting tenants.");
    computeRegionDao.deleteAll();
    sb.append("Deleting compute regions.");
    return "{ \"message\" : \"" + sb.toString() + "\"}";
  }
}
