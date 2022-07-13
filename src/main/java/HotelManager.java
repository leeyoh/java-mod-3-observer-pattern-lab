import java.util.ArrayList;
import java.util.List;

public class HotelManager {
    public static void main(String[] args) {
        Logger.getInstance().log("Managing hotel...");

        // create hotel rooms
        // create hotel floors
        // add hotel rooms to hotel floors
        // take actions on rooms and floors and examine your output to ensure you implemented the desired
        // behaviors
        HotelEmailService emailService = new HotelEmailService();
        HotelPushNotificationService notificationService = new HotelPushNotificationService();

        HotelFloor baseFloor = new HotelFloor(0);
        HotelFloor start = baseFloor;

        for(int f = 1; f < 10; f++){
            HotelFloor newFloor = new HotelFloor(f);
            for(int r = 0; r < 10; r++){
                HotelRoom newRoom = new HotelRoom();
                newRoom.addCheckinObserver(emailService);
                newRoom.addCheckinObserver(notificationService);
                newRoom.book("Guest " + String.valueOf(f) + String.valueOf(r));
                newFloor.addHotelRoom(newRoom);
            }
            Logger.getInstance().log("Floor ----- " +  String.valueOf(f));
            baseFloor.addFloor(newFloor);
            baseFloor = newFloor;
        }
        while(start.getNextFloor() != null){
            final int[] i = {0};
            start.getRoom().forEach((hotelRoom) -> {
                hotelRoom.checkIn(String.valueOf(i[0]));
                i[0] = i[0] + 1;
            });
            //start.clean();
            start = start.getNextFloor();
        }
    }
}

interface HotelRoomInterface {
    void book(String guestName);
    void clean();
    void checkIn(String guestName);
}

class HotelRoom implements HotelRoomInterface {
    private List<RoomCheckinObserver> checkinObservers = new ArrayList<RoomCheckinObserver>();

    public void book(String guestName) {
        Logger.getInstance().log("Booked a room for " + guestName);
    }

    public void clean() {
        Logger.getInstance().log("Cleaned room");
    }

    public void addCheckinObserver(RoomCheckinObserver checkinObserver){
        checkinObservers.add(checkinObserver);
    }
    public void removeCheckinObserver(RoomCheckinObserver checkinObserver){
        checkinObservers.remove(checkinObserver);
    }
    public void checkIn(String guestName){
        Logger.getInstance().log(guestName + "Checked in");
        checkinObservers.forEach((checkinObserver -> checkinObserver.update(guestName)));
    }
}

class HotelFloor implements HotelRoomInterface {
    private List<HotelRoomInterface> hotelRooms = new ArrayList<HotelRoomInterface>();
    private int sequenceNumber;
    private HotelFloor nextFloor;

    public HotelFloor(int sequenceNumber){
        this.sequenceNumber = sequenceNumber;
    }
    //Mass Booking of one guest for the entire floor?
    public void book(String guestName) {
        hotelRooms.forEach(child -> {
            child.book(guestName);
        });
    }
    public List<HotelRoomInterface> getRoom(){
        return this.hotelRooms;
    }
    public HotelFloor getNextFloor(){
        return nextFloor;
    }
    public void clean() {
        hotelRooms.forEach(child -> child.clean());
    }
    //mass Checkin
    @Override
    public void checkIn(String guestName) {
        hotelRooms.forEach(child -> {
            child.checkIn(guestName);
        });
    }

    public void addFloor(HotelFloor hotelFloor){
        nextFloor = hotelFloor;
    }

    public void addHotelRoom(HotelRoomInterface hotelRoom) {
        hotelRooms.add(hotelRoom);
    }

    public void removeHotelRoom(HotelRoomInterface hotelRoom) {
        hotelRooms.remove(hotelRoom);
    }
}