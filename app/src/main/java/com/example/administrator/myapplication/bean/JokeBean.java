package com.example.administrator.myapplication.bean;

/**
 * @author : Administrator
 * @time : 14:04
 * @for :
 */
public class JokeBean {
    /**
     * content : 某先生是地方上的要人。一天，他像往常一样在书房里例览当日报纸，突然对妻子大声喊道：喂，安娜，你看到今天早报上的流言蜚语了吗？真可笑！他们说，你收拾行装出走了。你听见了吗？安娜、你在哪儿？安娜？啊！
     * hashId : 90B182FC7F74865B40B1E5807CFEBF41
     * unixtime : 1418745227
     * updatetime : 2014-12-16 23:53:47
     */


        private String content;
        private String hashId;
        private String unixtime;
        private String updatetime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHashId() {
            return hashId;
        }

        public void setHashId(String hashId) {
            this.hashId = hashId;
        }

        public String getUnixtime() {
            return unixtime;
        }

        public void setUnixtime(String unixtime) {
            this.unixtime = unixtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        @Override
        public String toString() {
            return "JokeBean{" +
                    "content='" + content + '\'' +
                    ", hashId='" + hashId + '\'' +
                    ", unixtime='" + unixtime + '\'' +
                    ", updatetime='" + updatetime + '\'' +
                    '}';
    }
}
