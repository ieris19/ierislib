package com.ieris19.lib.net.auth.id;

import javax.crypto.SealedObject;
import java.util.UUID;

public final class SessionId extends AbstractIdentifier {
    private SealedObject verificationKey;

    public SealedObject getVerificationKey() {
        return verificationKey;
    }

    public void setVerificationKey(SealedObject verificationKey) {
        this.verificationKey = verificationKey;
    }

    public SessionId(UUID id) {
        this.setId(id);
    }


}
