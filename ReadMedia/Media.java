package appdevin.com.read_photos;

class Media {

    private String path;
    private String data;
    private int type;

    public Media(String path, String data, int type) {
        this.path = path;
        this.data = data;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {

        String typeS;
        switch (type){
            case 0:
                typeS = "Images";
                break;
            case 1:
                typeS = "Videos";
                break;
            case 2:
                typeS = "Audio";
                break;
            default:
                typeS = "Unknown";

        }

        return typeS;

    }

    public void setType(int type) {
        this.type = type;
    }
}

