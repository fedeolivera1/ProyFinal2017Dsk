package gpd.ws.parsers;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.dominio.util.EstadoSinc;
import gpd.exceptions.ParsersException;
import gpd.types.Fecha;
import gpw.webservice.proxy.ParamObtPedidosNoSinc;
import gpw.webservice.proxy.ParamPedidoASinc;
import gpw.webservice.proxy.ParamPedidoConfirmado;
import gpw.webservice.proxy.ParamPedidoLinea;
import gpw.webservice.proxy.ParamRecPedidosASinc;

public class ParserParamPedido {

	private static Logger logger = Logger.getLogger(ParserParamPedido.class);
	
	public static ParamObtPedidosNoSinc parseParamObtPedidosNoSinc(Fecha fechaDesde, Fecha fechaHasta) throws ParsersException {
		ParamObtPedidosNoSinc param = new ParamObtPedidosNoSinc();
		try {
			if(fechaDesde != null && fechaHasta != null) {
				param.setFechaDesde(fechaDesde.getAsXMLGregorianCalendar(Fecha.AMD));
				param.setFechaHasta(fechaHasta.getAsXMLGregorianCalendar(Fecha.AMD));
			} else {
				throw new ParsersException("Los datos del tipo 'ParamObtPedidoSinc' son obligatorios.");
			}
		} catch (Exception e) {
			logger.error("Excepcion al parsear en parseParamObtPedidosNoSinc: ", e);
			throw new ParsersException(e);
		}
		return param;
	}
	
	public static ParamRecPedidosASinc parseParamRecPedidosASinc(Map<Pedido, EstadoSinc> listaPedidosAConf, List<Pedido> listaPedidosNoSinc) throws ParsersException {
		ParamRecPedidosASinc param = new ParamRecPedidosASinc();
		try {
			//seteo lista de pedidos confirmados desde dsk a web (a sinc en web con S)
			if(listaPedidosAConf != null && !listaPedidosAConf.isEmpty()) {
				for(Map.Entry<Pedido, EstadoSinc> entry : listaPedidosAConf.entrySet()) {
					Pedido pedido = entry.getKey();
					EstadoSinc estSinc = entry.getValue();
					ParamPedidoConfirmado paramPc = new ParamPedidoConfirmado();
					paramPc.setIdPersona(pedido.getPersona().getIdPersona());
					paramPc.setFechaHora(pedido.getFechaHora().getAsXMLGregorianCalendar(Fecha.AMDHMS));
					paramPc.setEstadoSinc(estSinc.getAsInt());
					param.getListaPedidoConfirmado().add(paramPc);
				}
			}
			//seteo lista de pedidos no sinc desde dsk a web
			if(listaPedidosNoSinc != null && !listaPedidosNoSinc.isEmpty()) {
				for(Pedido pedido : listaPedidosNoSinc) {
					ParamPedidoASinc paramPas = new ParamPedidoASinc();
					paramPas.setIdPersona(pedido.getPersona().getIdPersona());
					paramPas.setFechaHora(pedido.getFechaHora().getAsXMLGregorianCalendar(Fecha.AMDHMS));
					paramPas.setEstado(pedido.getEstado().getEstadoPedido());
					paramPas.setFechaProg(pedido.getFechaProg() != null ? pedido.getFechaProg().getAsXMLGregorianCalendar(Fecha.AMD) : null);
					paramPas.setHoraProg(pedido.getHoraProg() != null ? pedido.getHoraProg().getHoraAsXMLGregorianCalendar() : null);
					paramPas.setOrigen(pedido.getOrigen().getOrigen());
					paramPas.setTotal(pedido.getTotal());
					paramPas.setSinc(pedido.getSinc().getSinc());
					paramPas.setUltAct(pedido.getUltAct().getAsXMLGregorianCalendar(Fecha.AMDHMS));
					for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
						ParamPedidoLinea ppl = new ParamPedidoLinea();
						ppl.setIdProducto(pl.getProducto().getIdProducto());
						ppl.setCantidad(pl.getCantidad());
						ppl.setPrecioUnit(pl.getPrecioUnit());
						paramPas.getListaPedidoLinea().add(ppl);
					}
					param.getListaPedidosASinc().add(paramPas);
				}
//			} else {
//				throw new ParsersException("Los datos del tipo 'ParamRecPedidoSinc' son obligatorios.");
			}
		} catch (Exception e) {
			logger.error("Excepcion al parsear en parseParam: ", e);
			throw new ParsersException(e);
		}
		return param;
	}
	
}
