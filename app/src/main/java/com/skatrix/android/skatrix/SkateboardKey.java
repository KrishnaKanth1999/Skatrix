package com.skatrix.android.skatrix;

import com.google.firebase.database.IgnoreExtraProperties;

public class SkateboardKey {


        public String Skateboard1;


        public SkateboardKey(){
            //this constructor is required
        }

        public void setkey(String no) {
            this.Skateboard1 = no;

        }

        public String skateid() {
            return Skateboard1;
        }
}
