package com.alfianfaiz.app.revotech.AdapterMain;

/**
 * Created by USRER on 3/23/2017.
 */

public class TabelAttach {
    private String idAttach,idVisit,imgUrl,idATM,hub,tanggal;

    public TabelAttach(String idAttach, String idVisit, String imgUrl, String idATM, String hub, String tanggal) {
        this.idAttach = idAttach;
        this.idVisit = idVisit;
        this.imgUrl = imgUrl;
        this.idATM = idATM;
        this.hub = hub;
        this.tanggal = tanggal;
    }

    public String getIdAttach() {
        return idAttach;
    }

    public void setIdAttach(String idAttach) {
        this.idAttach = idAttach;
    }

    public String getIdVisit() {
        return idVisit;
    }

    public void setIdVisit(String idVisit) {
        this.idVisit = idVisit;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIdATM() {
        return idATM;
    }

    public void setIdATM(String idATM) {
        this.idATM = idATM;
    }

    public String getHub() {
        return hub;
    }

    public void setHub(String hub) {
        this.hub = hub;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
