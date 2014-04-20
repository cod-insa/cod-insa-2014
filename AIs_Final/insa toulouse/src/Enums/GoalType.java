package Enums;

public enum GoalType {
	ATTACK,
	DEFEND,
	MOVING_RESSOURCES, // Echange de ressources entre deux bases
	COUNTRY_CLOSE_RESSOURCES, // Ressources base - noeud le plus proche
	COUNTRY_FOREIGN_RESSOURCES // Ressources base - noeud lointain
}
