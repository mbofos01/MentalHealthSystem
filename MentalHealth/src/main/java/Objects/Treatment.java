package Objects;

public class Treatment {
	private int treatment_id;
	private int patient_id;
	private int doctor_id;
	private int dose;
	private String comments;
	private boolean warning;
	private int drug_id;
	private boolean accepted;

	public Treatment() {
		accepted = false;
	}

	/**
	 * @return the treatment_id
	 */
	public int getTreatment_id() {
		return treatment_id;
	}

	/**
	 * @param treatment_id the treatment_id to set
	 */
	public void setTreatment_id(int treatment_id) {
		this.treatment_id = treatment_id;
	}

	/**
	 * @return the patient_id
	 */
	public int getPatient_id() {
		return patient_id;
	}

	/**
	 * @param patient_id the patient_id to set
	 */
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	/**
	 * @return the doctor_id
	 */
	public int getDoctor_id() {
		return doctor_id;
	}

	/**
	 * @param doctor_id the doctor_id to set
	 */
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	/**
	 * @return the dose
	 */
	public int getDose() {
		return dose;
	}

	/**
	 * @param dose the dose to set
	 */
	public void setDose(int dose) {
		this.dose = dose;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the warning
	 */
	public boolean isWarning() {
		return warning;
	}

	/**
	 * @param warning the warning to set
	 */
	public void setWarning(boolean warning) {
		this.warning = warning;
	}

	/**
	 * @return the drug_id
	 */
	public int getDrug_id() {
		return drug_id;
	}

	/**
	 * @param drug_id the drug_id to set
	 */
	public void setDrug_id(int drug_id) {
		this.drug_id = drug_id;
	}

	/**
	 * @return the accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public String toString() {
		return "Treatment [treatment_id=" + treatment_id + ", patient_id=" + patient_id + ", doctor_id=" + doctor_id
				+ ", dose=" + dose + ", comments=" + comments + ", warning=" + warning + ", drug_id=" + drug_id
				+ ", accepted=" + accepted + "]";
	}

}
