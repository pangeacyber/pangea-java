package cloud.pangeacyber.pangea;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(builderMethodName = "")
public class BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("config_id")
	String configID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transfer_method")
	TransferMethod transferMethod;
}
