package com.tuvn.webNA.views.asistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.tuvn.webNA.controllers.AsistenciaController;
import com.tuvn.webNA.controllers.impl.AsistenciaControllerImpl;



@ManagedBean
@ViewScoped
public class ReporteAsistenciaView implements Serializable{

	private static final long serialVersionUID = 1L;

	private HorizontalBarChartModel hbarModel;

	private AsistenciaController asistenciaController;
	
	private List<Object> listaAsistencias;
	
	public ReporteAsistenciaView() {
		
	}
	
	@PostConstruct
	public void init() {
		asistenciaController = new AsistenciaControllerImpl();
		
		String cod = getId();
		
		listaAsistencias = asistenciaController.obtenerReporteAsistencia(cod);
		
		createHorizontalBarModel();
	}
	
	private String getId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("matricula");
		return getParam;
	}
	
	public void createHorizontalBarModel() {
        hbarModel = new HorizontalBarChartModel();
        ChartData data = new ChartData();

        HorizontalBarChartDataSet hbarDataSet = new HorizontalBarChartDataSet();
        hbarDataSet.setLabel("Dias asistidos");
        
        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();


        for (int i=0; i< listaAsistencias.size(); i++){

	        Object[] row = (Object[]) listaAsistencias.get(i);
	
	        List<Object> xx = Arrays.asList(row);
	
	        labels.add(xx.get(0)+" "+xx.get(1));
	
	        Integer n = Integer.valueOf(xx.get(2).toString());
	
	        values.add(n);
        }
        
        hbarDataSet.setData(values);
  
        data.setLabels(labels);
        hbarModel.setData(data);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        hbarDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        hbarDataSet.setBorderColor(borderColor);
        hbarDataSet.setBorderWidth(1);

        data.addChartDataSet(hbarDataSet);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addXAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Reporte de asistencias");
        options.setTitle(title);

        hbarModel.setOptions(options);
    }

	
	//----------------
	public HorizontalBarChartModel getHbarModel() {
		return hbarModel;
	}

	public void setHbarModel(HorizontalBarChartModel hbarModel) {
		this.hbarModel = hbarModel;
	}
	
	
	

}
