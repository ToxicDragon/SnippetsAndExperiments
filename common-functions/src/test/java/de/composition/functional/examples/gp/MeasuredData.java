package de.composition.functional.examples.gp;

import java.math.BigDecimal;

import org.joda.time.DateTime;

public class MeasuredData {

	final String measuringDeviceId;

	final DateTime pointOfTime;

	final BigDecimal value;

	final String measuringType;

	public MeasuredData(String measuringDeviceId, DateTime pointOfTime, BigDecimal value, String measuringType) {
		this.measuringDeviceId = measuringDeviceId;
		this.pointOfTime = pointOfTime;
		this.value = value;
		this.measuringType = measuringType;
	}

	public String getMeasuringDeviceId() {
		return measuringDeviceId;
	}

	public DateTime getPointOfTime() {
		return pointOfTime;
	}

	public BigDecimal getValue() {
		return value;
	}

	public String getMeasuringType() {
		return measuringType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((measuringDeviceId == null) ? 0 : measuringDeviceId.hashCode());
		result = prime * result + ((measuringType == null) ? 0 : measuringType.hashCode());
		result = prime * result + ((pointOfTime == null) ? 0 : pointOfTime.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MeasuredData other = (MeasuredData) obj;
		if (measuringDeviceId == null) {
			if (other.measuringDeviceId != null) {
				return false;
			}
		} else if (!measuringDeviceId.equals(other.measuringDeviceId)) {
			return false;
		}
		if (measuringType == null) {
			if (other.measuringType != null) {
				return false;
			}
		} else if (!measuringType.equals(other.measuringType)) {
			return false;
		}
		if (pointOfTime == null) {
			if (other.pointOfTime != null) {
				return false;
			}
		} else if (!pointOfTime.equals(other.pointOfTime)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MeasuredData [measuringDeviceId=" + measuringDeviceId + ", pointOfTime=" + pointOfTime + ", value="
				+ value + ", measuringType=" + measuringType + "]";
	}

}
