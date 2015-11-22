package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vavasthi on 22/11/15.
 */
public class Group {
    @Id
    private Long id;
    private String description;
    private String facebookGroup;
}
