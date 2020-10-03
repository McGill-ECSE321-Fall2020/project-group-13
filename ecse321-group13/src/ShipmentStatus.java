import javax.persistence.Entity;

@Entity
public enum ShipmentStatus{
	OnRoute, Delivered, Canceled, ReadyForPickup
}
