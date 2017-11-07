package com.daocheng.girlshop.entity.shdiai;

import com.daocheng.girlshop.entity.ServiceResult;

import java.util.List;

/**
 * 项目名称：girlshop
 * 类描述：首页数据
 * 创建人：jdd
 * 创建时间：2016/6/23 18:21
 * 修改人：jdd
 * 修改时间：2016/6/23 18:21
 * 修改备注：
 */

public class homedata extends ServiceResult{


    /**
     * mp4 : http://7vzn9p.com2.z0.glb.qiniucdn.com/钟点房哪些事-聚份子.mp4
     * pic1 : http://7vijhu.com1.z0.glb.clouddn.com/f1a40ec6731249789c0c439184a4225d.png
     * pic2 : http://7vijhu.com1.z0.glb.clouddn.com/9737b5b459e247489aa3a43ed31205af.png
     * pic3 : http://7vijhu.com1.z0.glb.clouddn.com/74b9a4192c354e82a56a2047b4d12cfb.png
     * title9 : ECA活动
     * icon9 : null
     * title10 : 新生必修课
     * icon10 :
     * record1 : [{"nickname":"刘敏sarah","head":null,"pk":0},{"nickname":"Kimi阚志永","head":null,"pk":0},{"nickname":"张菊萍","head":null,"pk":0},{"nickname":"张玲君","head":null,"pk":0},{"nickname":"武岳Aaron","head":null,"pk":0}]
     * record2 : [{"nickname":"刘敏sarah","head":null,"stamina":0},{"nickname":"Kimi阚志永","head":null,"stamina":0},{"nickname":"张菊萍","head":null,"stamina":0},{"nickname":"张玲君","head":null,"stamina":0},{"nickname":"武岳Aaron","head":null,"stamina":0}]
     */

    private String mp4;
    private String pic1;
    private String pic2;
    private String pic3;
    private String title3;
    private String title4;
    private String title5;
    private String title6;
    private String title7;
    private String title8;
    private String title9;
    private String icon9;
    private String title10;
    private String icon10;
    private String new9updateime;
    private String new10updateime;


    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getTitle4() {
        return title4;
    }

    public void setTitle4(String title4) {
        this.title4 = title4;
    }

    public String getTitle5() {
        return title5;
    }

    public void setTitle5(String title5) {
        this.title5 = title5;
    }

    public String getTitle6() {
        return title6;
    }

    public void setTitle6(String title6) {
        this.title6 = title6;
    }

    public String getTitle7() {
        return title7;
    }

    public void setTitle7(String title7) {
        this.title7 = title7;
    }

    public String getTitle8() {
        return title8;
    }

    public void setTitle8(String title8) {
        this.title8 = title8;
    }

    /**
     * nickname : 刘敏sarah
     * head : null
     * pk : 0
     */

    private List<Record1Bean> record1;
    /**
     * nickname : 刘敏sarah
     * head : null
     * stamina : 0
     */

    private List<Record2Bean> record2;

    private List<Record3Bean> record3;

    public String getNew9updateime() {
        return new9updateime;
    }

    public void setNew9updateime(String new9updateime) {
        this.new9updateime = new9updateime;
    }

    public String getNew10updateime() {
        return new10updateime;
    }

    public void setNew10updateime(String new10updateime) {
        this.new10updateime = new10updateime;
    }

    public List<Record3Bean> getRecord3() {
        return record3;
    }

    public void setRecord3(List<Record3Bean> record3) {
        this.record3 = record3;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getTitle9() {
        return title9;
    }

    public void setTitle9(String title9) {
        this.title9 = title9;
    }

    public String getIcon9() {
        return icon9;
    }

    public void setIcon9(String icon9) {
        this.icon9 = icon9;
    }

    public String getTitle10() {
        return title10;
    }

    public void setTitle10(String title10) {
        this.title10 = title10;
    }

    public String getIcon10() {
        return icon10;
    }

    public void setIcon10(String icon10) {
        this.icon10 = icon10;
    }

    public List<Record1Bean> getRecord1() {
        return record1;
    }

    public void setRecord1(List<Record1Bean> record1) {
        this.record1 = record1;
    }

    public List<Record2Bean> getRecord2() {
        return record2;
    }

    public void setRecord2(List<Record2Bean> record2) {
        this.record2 = record2;
    }

    public static class Record1Bean {
        private String nickname;
        private String head;
        private int pk;
        private String pk1;

        public String getPk1() {
            return pk1;
        }

        public void setPk1(String pk1) {
            this.pk1 = pk1;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getPk() {
            return pk;
        }

        public void setPk(int pk) {
            this.pk = pk;
        }
    }

    public static class Record3Bean {
        private String nickname;
        private String head;
        private String point;

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

    }

    public static class Record2Bean {
        private String nickname;
        private String head;
        private int stamina;
        private String stamina1;

        public String getStamina1() {
            return stamina1;
        }

        public void setStamina1(String stamina1) {
            this.stamina1 = stamina1;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getStamina() {
            return stamina;
        }

        public void setStamina(int stamina) {
            this.stamina = stamina;
        }
    }
}
