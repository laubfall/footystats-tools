package de.footystats.tools.services.footy.dls;

/**
 * Bits that enable the download of a specific csv file.
 */
public enum FileTypeBit {
	LEAGUE(1),
	TEAM(2),
	TEAM2(4),
	PLAYER(8),
	MATCH(16)
	;
	private int bit;

	FileTypeBit(int bit) {
		this.bit = bit;
	}

	public int getBit() {
		return bit;
	}
}
