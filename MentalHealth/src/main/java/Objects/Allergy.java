package Objects;

public class Allergy {
	private int patient_id;
	private int allergy_id;
	private int drug_id;

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
	 * @return the allergy_id
	 */
	public int getAllergy_id() {
		return allergy_id;
	}

	/**
	 * @param allergy_id the allergy_id to set
	 */
	public void setAllergy_id(int allergy_id) {
		this.allergy_id = allergy_id;
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

}
