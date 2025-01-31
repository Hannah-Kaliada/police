package il.pacukievich.police.entities;

public enum TypeOfCrime {
		MURDER("Убийство"),
		THEFT("Кража"),
		ASSAULT("Нападение"),
		ROBBERY("Разбой"),
		FRAUD("Мошенничество"),
		BURGLARY("Кража со взломом"),
		KIDNAPPING("Похищение человека"),
		DRUG_TRAFFICKING("Наркоторговля"),
		VANDALISM("Вандализм"),
		ARSON("Поджог"),
		BRIBERY("Взяточничество"),
		EXTORTION("Вымогательство"),
		FORGERY("Подделка документов"),
		CYBERCRIME("Киберпреступление"),
		HIJACKING("Угон"),
		TERRORISM("Терроризм"),
		HUMAN_TRAFFICKING("Торговля людьми"),
		MONEY_LAUNDERING("Отмывание денег"),
		PERJURY("Лжесвидетельство"),
		PUBLIC_INTOXICATION("Нахождение в общественном месте в состоянии опьянения");

		private final String russianName;

		TypeOfCrime(String russianName) {
				this.russianName = russianName;
		}

		public String getRussianName() {
				return russianName;
		}
}