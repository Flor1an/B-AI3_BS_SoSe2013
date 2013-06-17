package simrace_v1;

public class Rennleitung {
	
public static void main(String[] args) {
	SimRace nuerburgring = new SimRace();
	
	nuerburgring.startSchuss();
	nuerburgring.wartenAufZieleinlauf();
	nuerburgring.siegerehrung();
}
}
