package com.google.allenday.nanostream.pubsub;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Data class with information of bucket and folder of source FastQ file
 */
@DefaultCoder(AvroCoder.class)
public class GCSSourceData implements Serializable {

    private final static String BUCKET_KEY = "bucket";
    private final static String FOLDER_KEY = "folder";

    private String bucket;
    private String folder;

    public GCSSourceData() {
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public GCSSourceData(@Nonnull String bucket, @Nonnull String folder) {
        this.bucket = bucket;
        this.folder = folder;
    }

    public static GCSSourceData fromGCloudNotification(GCloudNotification gCloudNotification) {
        String folder = "/";
        int index = gCloudNotification.getName().lastIndexOf("/");
        if (index >= 0) {
            folder += gCloudNotification.getName().substring(0, index + 1);
        }
        return new GCSSourceData(gCloudNotification.getBucket(), folder);
    }

    public String getBucket() {
        return bucket;
    }

    public String getFolder() {
        return folder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GCSSourceData that = (GCSSourceData) o;
        return bucket.equals(that.bucket) &&
                folder.equals(that.folder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucket, folder);
    }

    @Override
    public String toString() {
        return "GCSSourceData{" +
                "bucket='" + bucket + '\'' +
                ", folder='" + folder + '\'' +
                '}';
    }

    public String toJsonString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(BUCKET_KEY, bucket);
        jsonObject.put(FOLDER_KEY, folder);
        return jsonObject.toString();
    }

    public static GCSSourceData fromJsonString(String jsonString){
        JSONObject jsonObject =  new JSONObject(jsonString);
        GCSSourceData gcsSourceData = new GCSSourceData();
        gcsSourceData.setBucket(jsonObject.getString(BUCKET_KEY));
        gcsSourceData.setFolder(jsonObject.getString(FOLDER_KEY));
        return gcsSourceData;
    }
}

