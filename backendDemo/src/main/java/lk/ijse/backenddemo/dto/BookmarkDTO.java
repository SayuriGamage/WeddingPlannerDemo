package lk.ijse.backenddemo.dto;


public class BookmarkDTO {


    private String userId;
    private String serviceId;

    public BookmarkDTO() {
    }

    public BookmarkDTO(String id, String userId, String serviceId) {

        this.userId = userId;
        this.serviceId = serviceId;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
