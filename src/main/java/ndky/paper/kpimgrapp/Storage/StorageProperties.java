package ndky.paper.kpimgrapp.Storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private String avatar, cert;

    public StorageProperties() {
    }

    public StorageProperties(String avatar, String cert) {
        this.avatar = avatar;
        this.cert = cert;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }
}
