package ru.tnkv.ecserver.utils.types;

public record Report(int id, String token, String application, String location, String exception, long timestamp, boolean archived) {
    public int getId() { return this.id; }
    public String getToken() { return this.token; }
    public String getApplication() { return this.application; }
    public String getLocation() { return this.location; }
    public String getException() { return this.exception; }
    public long getTimestamp() { return this.timestamp; }
    public boolean getArchived() { return this.archived; }
    public String toJson() {
        return "{" +
                "\"id\":" + getId() + "," +
                "\"token\":\"" + getToken() + "\"," +
                "\"application\":\"" + getApplication() + "\"," +
                "\"location\":\"" + getLocation() + "\"," +
                "\"exception\":\"" + getException() + "\"," +
                "\"timestamp\":" + getTimestamp() + "," +
                "\"archived\":" + getArchived() +
                "}";
    }
}
