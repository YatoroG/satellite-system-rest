package sys.domains.satellites;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sys.utils.SatelliteType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommunicationSatelliteParam.class, name = "COMMUNICATION"),
        @JsonSubTypes.Type(value = ImagingSatelliteParam.class, name = "IMAGE")
})
@AllArgsConstructor
@Getter
public abstract class SatelliteParam {
    private final SatelliteType type;
    private final String name;
    private final double batteryLevel;
}
