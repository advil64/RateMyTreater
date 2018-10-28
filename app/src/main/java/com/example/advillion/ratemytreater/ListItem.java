package com.example.advillion.ratemytreater;


public class ListItem {

    public String pic;
    public String head;
    public String desc;

    public ListItem(String pic, String head, String desc) {
        this.pic = pic;
        this.head = head;
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

}
