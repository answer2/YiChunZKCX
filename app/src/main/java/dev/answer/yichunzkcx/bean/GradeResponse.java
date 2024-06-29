package dev.answer.yichunzkcx.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.Gson;
import java.io.Serializable;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.Gson;
import org.json.JSONObject;

public class GradeResponse implements Parcelable {
    private int code;
    private String msg;
    private Data data;
    
    public GradeResponse(){
        
    }

    // Parcelable implementation for GradeResponse
    protected GradeResponse(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data = in.readParcelable(Data.class.getClassLoader(), Data.class);
    }

    public static final Creator<GradeResponse> CREATOR = new Creator<GradeResponse>() {
        @Override
        public GradeResponse createFromParcel(Parcel in) {
            return new GradeResponse(in);
        }

        @Override
        public GradeResponse[] newArray(int size) {
            return new GradeResponse[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static class Data implements Parcelable {
        private String xm1; // 姓名
        private String zkzh; // 准考证
        private String yw; // 语文
        private String sx; // 数学
        private String yy; // 英语
        private String wl; // 物理
        private String hx; // 化学
        private String zs; // 政史
        private String sd; // 生地
        private String jf; // 加分
        private String sycz; // 实验操作
        private String ty; // 体育
        private String zf; // 总分
        private String qxmc; // 区县名称
        private String xxmc; // 学校名称

        public Data() {
            
        }

        protected Data(Parcel in) {
            xm1 = in.readString();
            zkzh = in.readString();
            yw = in.readString();
            sx = in.readString();
            yy = in.readString();
            wl = in.readString();
            hx = in.readString();
            zs = in.readString();
            sd = in.readString();
            jf = in.readString();
            sycz = in.readString();
            ty = in.readString();
            zf = in.readString();
            qxmc = in.readString();
            xxmc = in.readString();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(xm1);
            dest.writeString(zkzh);
            dest.writeString(yw);
            dest.writeString(sx);
            dest.writeString(yy);
            dest.writeString(wl);
            dest.writeString(hx);
            dest.writeString(zs);
            dest.writeString(sd);
            dest.writeString(jf);
            dest.writeString(sycz);
            dest.writeString(ty);
            dest.writeString(zf);
            dest.writeString(qxmc);
            dest.writeString(xxmc);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        // Getter and Setter methods

        public String getXm1() {
            return xm1;
        }

        public void setXm1(String xm1) {
            this.xm1 = xm1;
        }

        public String getZkzh() {
            return zkzh;
        }

        public void setZkzh(String zkzh) {
            this.zkzh = zkzh;
        }

        public String getYw() {
            return yw;
        }

        public void setYw(String yw) {
            this.yw = yw;
        }

        public String getSx() {
            return sx;
        }

        public void setSx(String sx) {
            this.sx = sx;
        }

        public String getYy() {
            return yy;
        }

        public void setYy(String yy) {
            this.yy = yy;
        }

        public String getWl() {
            return wl;
        }

        public void setWl(String wl) {
            this.wl = wl;
        }

        public String getHx() {
            return hx;
        }

        public void setHx(String hx) {
            this.hx = hx;
        }

        public String getZs() {
            return zs;
        }

        public void setZs(String zs) {
            this.zs = zs;
        }

        public String getSd() {
            return sd;
        }

        public void setSd(String sd) {
            this.sd = sd;
        }

        public String getJf() {
            return jf;
        }

        public void setJf(String jf) {
            this.jf = jf;
        }

        public String getSycz() {
            return sycz;
        }

        public void setSycz(String sycz) {
            this.sycz = sycz;
        }

        public String getTy() {
            return ty;
        }

        public void setTy(String ty) {
            this.ty = ty;
        }

        public String getZf() {
            return zf;
        }

        public void setZf(String zf) {
            this.zf = zf;
        }

        public String getQxmc() {
            return qxmc;
        }

        public void setQxmc(String qxmc) {
            this.qxmc = qxmc;
        }

        public String getXxmc() {
            return xxmc;
        }

        public void setXxmc(String xxmc) {
            this.xxmc = xxmc;
        }

        public String toJsonString() {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("xm1", xm1);
                jsonObject.put("zkzh", zkzh);
                jsonObject.put("yw", yw);
                jsonObject.put("sx", sx);
                jsonObject.put("yy", yy);
                jsonObject.put("wl", wl);
                jsonObject.put("hx", hx);
                jsonObject.put("zs", zs);
                jsonObject.put("sd", sd);
                jsonObject.put("jf", jf);
                jsonObject.put("sycz", sycz);
                jsonObject.put("ty", ty);
                jsonObject.put("zf", zf);
                jsonObject.put("qxmc", qxmc);
                jsonObject.put("xxmc", xxmc);

                return jsonObject.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
