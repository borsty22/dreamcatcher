package edu.htw.magw.dreamcatcher;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.invient.vaadin.charts.Color.RGBA;
import com.invient.vaadin.charts.InvientCharts;
import com.invient.vaadin.charts.InvientCharts.DateTimePoint;
import com.invient.vaadin.charts.InvientCharts.DateTimeSeries;
import com.invient.vaadin.charts.InvientCharts.SeriesType;
import com.invient.vaadin.charts.InvientChartsConfig;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.AxisTitle;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.Grid;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.MinorGrid;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotBand;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotBand.NumberRange;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.PlotLabel;
import com.invient.vaadin.charts.InvientChartsConfig.DateTimeAxis;
import com.invient.vaadin.charts.InvientChartsConfig.MarkerState;
import com.invient.vaadin.charts.InvientChartsConfig.NumberYAxis;
import com.invient.vaadin.charts.InvientChartsConfig.SeriesState;
import com.invient.vaadin.charts.InvientChartsConfig.SplineConfig;
import com.invient.vaadin.charts.InvientChartsConfig.SymbolMarker;
import com.invient.vaadin.charts.InvientChartsConfig.SymbolMarker.Symbol;
import com.invient.vaadin.charts.InvientChartsConfig.XAxis;
import com.invient.vaadin.charts.InvientChartsConfig.YAxis;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import edu.htw.magw.dreamcatcher.domain.dao.DeviceDao;

public class DreamcatcherChartWindow extends Window {

	@Autowired
	DeviceDao deviceDao ;
	
	private static final long serialVersionUID = 1L;

	public DreamcatcherChartWindow(Map<String, List<Value>> dataMap, DateTime beginDateTime) {
		
		HorizontalLayout mainLayout = new HorizontalLayout();
		setContent(mainLayout);
		setSizeFull();
		mainLayout.setSizeFull();
		setCaption("Dreamcatcher Charts");

        Label lblAppTitle = new Label("Demo Gallery for Invient Charts");
        lblAppTitle.setSizeFull();
        lblAppTitle.setStyleName("v-label-app-title");
        
		InvientChartsConfig chartConfig = new InvientChartsConfig();
        chartConfig.getGeneralChartConfig().setType(SeriesType.SPLINE);
        
        chartConfig.getTitle().setText("sleeping level during two days");
        chartConfig
                .getSubtitle()
                .setText(
                        "");

        chartConfig
                .getTooltip()
                .setFormatterJsFunc(
                        "function() {"
                                + " return '' + $wnd.Highcharts.dateFormat('%e. %b %Y, %H:00', this.x) +': '+ this.y +' activity/min'; "
                                + "}");
        
        DateTimeAxis xAxis = new DateTimeAxis();
        LinkedHashSet<XAxis> xAxesSet = new LinkedHashSet<InvientChartsConfig.XAxis>();
        xAxesSet.add(xAxis);
        chartConfig.setXAxes(xAxesSet);
        
        NumberYAxis yAxis = new NumberYAxis();
        yAxis.setTitle(new AxisTitle("sleep level (snores/min)"));
        yAxis.setMin(0.0);
        yAxis.setMinorGrid(new MinorGrid());
        yAxis.getMinorGrid().setLineWidth(0);
        yAxis.setGrid(new Grid());
        yAxis.getGrid().setLineWidth(0);

        NumberPlotBand numberBand = new NumberPlotBand("aufmerksam");
        numberBand.setRange(new NumberRange(30000.0, 14000.0));
        numberBand.setColor(new RGBA(68, 170, 213, 0.1f));
        numberBand.setLabel(new PlotLabel("aufmerksam"));
        numberBand.getLabel().setStyle("{ color: '#606060' }");
        yAxis.getPlotBands().add(numberBand);

        numberBand = new NumberPlotBand("entspannt");
        numberBand.setRange(new NumberRange(14000.0, 10000.0));
        numberBand.setColor(new RGBA(0, 0, 0, 0.0f));
        numberBand.setLabel(new PlotLabel("entspannt"));
        numberBand.getLabel().setStyle("{ color: '#606060' }");
        yAxis.getPlotBands().add(numberBand);

        numberBand = new NumberPlotBand("leichter Schlaf");
        numberBand.setRange(new NumberRange(10000.0, 7000.0));
        numberBand.setColor(new RGBA(68, 170, 213, 0.1f));
        numberBand.setLabel(new PlotLabel("leichter Schlaf"));
        numberBand.getLabel().setStyle("{ color: '#606060' }");
        yAxis.getPlotBands().add(numberBand);

        numberBand = new NumberPlotBand("Schlaf");
        numberBand.setRange(new NumberRange(7000.0, 5000.0));
        numberBand.setColor(new RGBA(0, 0, 0, 0.0f));
        numberBand.setLabel(new PlotLabel("Schlaf"));
        numberBand.getLabel().setStyle("{ color: '#606060' }");
        yAxis.getPlotBands().add(numberBand);

        numberBand = new NumberPlotBand("Übergang in den Tiefschlaf");
        numberBand.setRange(new NumberRange(5000.0, 3000.0));
        numberBand.setColor(new RGBA(68, 170, 213, 0.1f));
        numberBand.setLabel(new PlotLabel("Übergang in den Tiefschlaf"));
        numberBand.getLabel().setStyle("{ color: '#606060' }");
        yAxis.getPlotBands().add(numberBand);

        numberBand = new NumberPlotBand("Tiefschlaf");
        numberBand.setRange(new NumberRange(3000.0, 1500.0));
        numberBand.setColor(new RGBA(0, 0, 0, 0.0f));
        numberBand.setLabel(new PlotLabel("Tiefschlaf"));
        numberBand.getLabel().setStyle("{ color: '#606060' }");
        yAxis.getPlotBands().add(numberBand);

        numberBand = new NumberPlotBand("REM Schlaf");
        numberBand.setRange(new NumberRange(1500.0, 0.0));
        numberBand.setColor(new RGBA(68, 170, 213, 0.1f));
        numberBand.setLabel(new PlotLabel("REM Schlaf"));
        numberBand.getLabel().setStyle("{ color: '#606060' }");
        yAxis.getPlotBands().add(numberBand);
        
        LinkedHashSet<YAxis> yAxesSet = new LinkedHashSet<InvientChartsConfig.YAxis>();
        yAxesSet.add(yAxis);
        chartConfig.setYAxes(yAxesSet);

        SplineConfig splineCfg = new SplineConfig();
        splineCfg.setLineWidth(2);
        splineCfg.setHoverState(new SeriesState());
        splineCfg.getHoverState().setLineWidth(6);
        
        SymbolMarker symbolMarker = new SymbolMarker(false);
        splineCfg.setMarker(symbolMarker);
        symbolMarker.setSymbol(Symbol.CIRCLE);
        symbolMarker.setHoverState(new MarkerState());
        symbolMarker.getHoverState().setEnabled(true);
        symbolMarker.getHoverState().setRadius(5);
        symbolMarker.getHoverState().setLineWidth(1);

        if (beginDateTime == null)
        	beginDateTime = DateTime.now() ;
        
        splineCfg.setPointStart((double) getPointStartDate(beginDateTime.getYear(), beginDateTime.getMonthOfYear(), beginDateTime.getDayOfMonth()));
        splineCfg.setPointInterval(360000.0);
        chartConfig.addSeriesConfig(splineCfg);

        InvientCharts chart = new InvientCharts(chartConfig);
        chart.setSizeFull() ;
        
        DateTimeSeries series ;
        
        for (Entry<String, List<Value>> entry : dataMap.entrySet())
        {
        	series = new DateTimeSeries(entry.getKey().toString(), splineCfg, true);
        	for (Value value : entry.getValue())
        	{
        		series.addPoint(new DateTimePoint(series, value.getDateTime().toDate(), value.getValue())) ;
        	}
        	
        	chart.addSeries(series);
        }
        
		mainLayout.addComponent(chart) ;
	}

	private static long getPointStartDate(int year, int month, int day) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

}
