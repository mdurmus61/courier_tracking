package md.ct.response;

public class StreamGeolocationResponse {
    private Long retValue;

    public StreamGeolocationResponse(Long retValue) {
        this.retValue = retValue;
    }

    public Long getRetValue() {
        return retValue;
    }

    public void setRetValue(Long retValue) {
        this.retValue = retValue;
    }
}
