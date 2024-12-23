package org.geldikYoktunuz;

public enum CargoStatus {
    CANCELLED("İptal Edildi"),
    PENDING_APPROVAL("Onay Bekliyor"),
    IN_PROCESS("İşleme Alındı"),
    OUT_FOR_DELIVERY("Teslimatta"),
    DELIVERED("Teslim Edildi");
    private final String description;

    CargoStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

