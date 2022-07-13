public class HotelPushNotificationService implements RoomCheckinObserver{
    public void update(Object guestName){
        Logger.getInstance().log("Registered " + guestName + " for push notification updates");
    }
}
