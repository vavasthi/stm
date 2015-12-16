package com.avasthi.trial.app;

/**
 * Created by vavasthi on 15/12/15.
 */
public class ObjectFactoryKey {

    public ObjectFactoryKey(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectFactoryKey that = (ObjectFactoryKey) o;

        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    private String path;
    private String name;
}
