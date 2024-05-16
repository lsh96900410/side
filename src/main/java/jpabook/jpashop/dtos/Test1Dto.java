package jpabook.jpashop.dtos;

public class Test1Dto {



        private int idx = 1 ;
        private String contents = "content1";
        private String complete_yn = " 테스트중 22 ";
        private String user_id = " 테스트중 33 ";

        public Test1Dto() {}

        public Test1Dto(int idx, String contents, String complete_yn, String user_id) {
            this.idx = idx;
            this.contents = contents;
            this.complete_yn = complete_yn;
            this.user_id = user_id;
        }

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getComplete_yn() {
            return complete_yn;
        }

        public void setComplete_yn(String complete_yn) {
            this.complete_yn = complete_yn;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        @Override
        public String toString() {
            return "TodoVO [idx=" + idx + ", contents=" + contents + ", complete_yn=" + complete_yn + ", user_id=" + user_id
                    + "]";
        }

}
