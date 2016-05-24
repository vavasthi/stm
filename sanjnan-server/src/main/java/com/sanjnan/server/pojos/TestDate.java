package com.sanjnan.server.pojos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sanjnan.server.serializers.H2ODateTimeDeserializer;
import com.sanjnan.server.serializers.H2ODateTimeSerializer;
import org.joda.time.DateTime;

/**
 * Created by vinay on 3/3/16.
 */
public class TestDate extends Base {

  @JsonSerialize(using = H2ODateTimeSerializer.class)
  @JsonDeserialize(using = H2ODateTimeDeserializer.class)
  public DateTime date;

}
