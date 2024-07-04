package dev.answer.yichunzkcx.util;

import android.app.Activity;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Set;
import java.util.Collection;
import java.util.Enumeration;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class PropertiesUtil {

  private Properties properties;

  private String path;

  private BufferedReader input;

  private BufferedWriter out;

  public PropertiesUtil(Activity activity, String dir, String file) {
    this(init(activity, dir, file));
  }

  public PropertiesUtil(File file) {
    this(file.getAbsolutePath());
  }

  public PropertiesUtil(String path) {
    if (isNotNull(path)) {
      this.path = path;
      properties = new Properties();
      try {
        input = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
        properties.load(input);
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }
  }

  private static File init(Activity activity, String dir, String file) {
    try {
      File dirFile = new File(activity.getDataDir().toString() + dir);
      File properties_ = new File(dirFile.toString() + file);
      if (!dirFile.exists()) dirFile.mkdirs();
      if (!properties_.exists()) properties_.createNewFile();
      return properties_;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public PropertiesUtil store(String path, String comment) {
    if (isNotNull(path)) {
      try {
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
        properties.store(out, comment);
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }
    return this;
  }

  public PropertiesUtil store(String comment) {
    store(this.path, comment);
    return this;
  }

  public <V extends Object> Collection<V> values() {
    if (isNotNull(properties)) {
      return (Collection<V>) properties.values();
    }
    return null;
  }

  public <K extends Object> Set<K> keySet() {
    if (isNotNull(properties)) {
      return (Set<K>) properties.keySet();
    }
    return null;
  }

  public Enumeration<?> propertyNames() {
    if (isNotNull(properties)) {
      return properties.propertyNames();
    }
    return null;
  }

  public Properties properties() {
    if (isNotNull(properties)) {
      return properties;
    }
    return new Properties();
  }

  public PropertiesUtil remove(String key) {
    if (isNotNull(key) && isNotNull(properties) && properties.keySet().contains(key)) {
      properties.remove(key);
    }
    return this;
  }

  public PropertiesUtil closeFileStream() {
    if (isNotNull(input) && isNotNull(out)) {
      try {
        input.close();
        out.close();
        input = null;
        out = null;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return this;
  }

  public void close() {
    if (isNotNull(properties)) {
      properties.clear();
    }
    properties = null;
  }

  public PropertiesUtil setProperty(String key, Object value) {
    try {
      if (isNotNull(key) && isNotNull(value)) {
        properties.setProperty(key, String.valueOf(value));
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return this;
  }

  public String getProperty(String key) {
    if (isNotNull(key)) {
      return properties.getProperty(key);
    }
    return "";
  }

  public String getString(String key) {
    if (isNotNull(key)) {
      return properties.getProperty(key);
    }
    return "";
  }

  public int getInt(String key) {
    if (isNotNull(key)) {
      return Integer.valueOf(properties.getProperty(key)).intValue();
    }
    return 0;
  }

  public boolean getBoolean(String key) {
    if (isNotNull(key)) {
      return Boolean.valueOf(properties.getProperty(key)).booleanValue();
    }
    return false;
  }

  public float getFloat(String key) {
    if (isNotNull(key)) {
      return Float.valueOf(properties.getProperty(key)).floatValue();
    }
    return 0;
  }

  public long getLong(String key) {
    if (isNotNull(key)) {
      return Long.valueOf(properties.getProperty(key)).longValue();
    }
    return 0;
  }

  public short getShort(String key) {
    if (isNotNull(key)) {
      return Short.valueOf(properties.getProperty(key)).shortValue();
    }
    return 0;
  }

  public String getProperty(String key, String defaultValue) {
    if (isNotNull(key) && isNotNull(defaultValue)) {
      return properties.getProperty(key, String.valueOf(defaultValue));
    }
    return defaultValue;
  }

  public String getString(String key, String defaultValue) {
    if (isNotNull(key) && isNotNull(defaultValue)) {
      return properties.getProperty(key);
    }
    return "";
  }

  public int getInt(String key, int defaultValue) {
    if (isNotNull(key) && isNotNull(defaultValue)) {
      return Integer.valueOf(properties.getProperty(key)).intValue();
    }
    return defaultValue;
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    if (isNotNull(key) && isNotNull(defaultValue)) {
      return Boolean.valueOf(properties.getProperty(key)).booleanValue();
    }
    return defaultValue;
  }

  public float getFloat(String key, float defaultValue) {
    if (isNotNull(key) && isNotNull(defaultValue)) {
      return Float.valueOf(properties.getProperty(key)).floatValue();
    }
    return defaultValue;
  }

  public long getLong(String key, long defaultValue) {
    if (isNotNull(key) && isNotNull(defaultValue)) {
      return Long.valueOf(properties.getProperty(key)).longValue();
    }
    return defaultValue;
  }

  public short getShort(String key, short defaultValue) {
    if (isNotNull(key) && isNotNull(defaultValue)) {
      return Short.valueOf(properties.getProperty(key)).shortValue();
    }
    return defaultValue;
  }

  public boolean isNotNull(Object o) {
    return o != null;
  }
}
