package test.types;

import static org.junit.Assert.*;

import org.junit.Test;

import gpd.types.Fecha;

public class FechaTest {

	@Test
	public void test() {
		Fecha fecha = new Fecha(2017, 05, 18);
		callToString(fecha);
		fecha = new Fecha(2000, 11, 24, 9, 58);
		callToString(fecha);
		fecha = new Fecha(1995, 12, 14, 18, 29, 59);
		callToString(fecha);
		fecha = new Fecha(23, 59);
		callToString(fecha);
		fecha = new Fecha(Fecha.AMD);
		callToString(fecha);
		assertNotNull(fecha);
	}
	
	private void callToString(Fecha fecha) {
		System.out.println(fecha.toString(Fecha.AMD));
		System.out.println(fecha.toString(Fecha.DMA));
		System.out.println(fecha.toString(Fecha.AMDHM));
		System.out.println(fecha.toString(Fecha.AMDHMS));
		System.out.println(fecha.toString(Fecha.HM));
		System.out.println(fecha.toString(Fecha.HMS));
		System.out.println("FORMATO DE LA FECHA: " + fecha.getFormato());
		System.out.println("--------------------------------------");
	}

}
