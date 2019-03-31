package com.tao.dto;

/**
 * ��¶��ɱ��ַDTO
 * 
 * @author Ulric
 * @version $Id: Exposer.java, v 1.0 2017��4��1�� ����7:35:05 Ulric Exp $
 */
public class Exposer {

    private boolean exposed;    //�Ƿ�����ɱ

    private String  md5;        //һ�ּ��ܴ�ʩ

    private long    inventoryId;//id

    private long    now;        //ϵͳ��ǰʱ�䣨���룩

    private long    start;      //��ɱ��ʼʱ��

    private long    end;        //��ɱ����ʱ��

    public Exposer(boolean exposed, String md5, long inventoryId) {
        super();
        this.exposed = exposed;
        this.md5 = md5;
        this.inventoryId = inventoryId;
    }

    public Exposer(boolean exposed, long inventoryId, long now, long start, long end) {
        super();
        this.exposed = exposed;
        this.inventoryId = inventoryId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long inventoryId) {
        super();
        this.exposed = exposed;
        this.inventoryId = inventoryId;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer [exposed=" + exposed + ", md5=" + md5 + ", inventoryId=" + inventoryId
               + ", now=" + now + ", start=" + start + ", end=" + end + "]";
    }

}
