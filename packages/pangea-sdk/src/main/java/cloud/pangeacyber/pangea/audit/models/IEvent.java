package cloud.pangeacyber.pangea.audit.models;

import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public interface IEvent {
	public void setTenantID(String tenantID);

	public String getTenantID();

	public static String canonicalize(IEvent event) throws JsonProcessingException {
		//FIXME: Check what happend if there is nested maps, do they order keys also? We DO NOT want that
		ObjectMapper mapper = JsonMapper
			.builder()
			.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
			.build();
		return mapper.writeValueAsString(event);
	}

	public static <EventType extends IEvent> IEvent fromRaw(Object raw, Class<EventType> eventClass)
		throws PangeaException {
		if (raw == null) {
			System.out.println("Event raw is null");
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		IEvent event;
		try {
			event = mapper.readValue(mapper.writeValueAsString(raw), eventClass);
		} catch (JsonProcessingException e) {
			System.out.println(e.toString());
			throw new PangeaException("Failed to process event", e);
		}
		return event;
	}
}
