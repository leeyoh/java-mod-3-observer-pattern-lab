public class HotelEmailService implements RoomCheckinObserver{
    public void update(Object guestName){
        Logger.getInstance().log("Sent email update to " + guestName);
    }
}
