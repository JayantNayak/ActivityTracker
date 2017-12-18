package com.hobby.jayant.activitytracker.utils;

/**
 * Created by I329687 on 12/1/2017.
 */

public  class Utils {
    public static class DummyItem{
        private String imageUrl;
        private String imageTitle;

        public DummyItem(String imageUrl, String imageTitle) {
            this.imageUrl = imageUrl;
            this.imageTitle = imageTitle;
        }
        public DummyItem(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getImageTitle() {
            return imageTitle;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if(getClass() != obj.getClass()){
                return false;
            }

            final DummyItem other = (DummyItem) obj;
            if (!this.imageUrl.equals(other.imageUrl)) {
                return false;
            }
           /* if (!this.imageTitle.equals(other.imageTitle)) {
                return false;
            }*/

            /*if(obj == this){
                return true;
            }*/
            return true;
        }


        //The hashCode() method of objects is used when you insert them into a HashTable, HashMap or HashSet.
        //Since we are using these objects to store in List, we are not going to override it.
        /*@Override
        public int hashCode() {
            return super.hashCode();
        }*/
    }
}
