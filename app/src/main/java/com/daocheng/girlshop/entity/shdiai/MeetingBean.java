package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.io.Serializable;
import java.util.List;

/**
 * 类名称：
 * 类描述：
 * 创建人：
 * 修改人：
 */
public class MeetingBean extends ServiceResult{


    /**
     * record : [{"id":23380,"logo":"http://file.changshuclub.com/9d451e0a10ee4f8987da0c3732ff68de.png","title":"测试直播001","number":"1540338","password":""},{"id":23381,"logo":"http://file.changshuclub.com/9d451e0a10ee4f8987da0c3732ff68de.png","title":"测试直播002","number":"1540338","password":"123456"}]
     * server : a.fsmeeting.com
     * port : 1089
     */

    private String server;
    private String port;
    private List<RecordBean> record;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean implements Serializable{
        /**
         * id : 23380
         * logo : http://file.changshuclub.com/9d451e0a10ee4f8987da0c3732ff68de.png
         * title : 测试直播001
         * number : 1540338
         * password :
         */

        private int id;
        private String logo;
        private String title;
        private String number;
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
