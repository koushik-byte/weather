package com.example.weather;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public final class XmlWeatherParser {

    public WeatherData parse(Document doc) {
        Element root = doc.getDocumentElement();

        String cityName = textContent(root, "city", "name");
        String countryCode = textContent(root, "city", "country");

        Double temperatureCelsius = attributeAsDouble(root, "temperature", "value");
        Integer humidityPercent = attributeAsInt(root, "humidity", "value");
        String weatherDescription = attribute(root, "weather", "value");
        Double windSpeed = attributeAsDouble(root, "speed", "value");
        Integer cloudiness = attributeAsInt(root, "clouds", "value");

        return new WeatherData(
                cityName,
                countryCode,
                temperatureCelsius,
                humidityPercent,
                weatherDescription,
                windSpeed,
                cloudiness
        );
    }

    private static String textContent(Element root, String tag, String childTag) {
        NodeList nodes = root.getElementsByTagName(tag);
        if (nodes.getLength() == 0) return null;
        Element e = (Element) nodes.item(0);
        NodeList children = e.getElementsByTagName(childTag);
        if (children.getLength() == 0) return null;
        return children.item(0).getTextContent();
    }

    private static String attribute(Element root, String tag, String attr) {
        NodeList nodes = root.getElementsByTagName(tag);
        if (nodes.getLength() == 0) return null;
        Element e = (Element) nodes.item(0);
        return e.hasAttribute(attr) ? e.getAttribute(attr) : null;
    }

    private static Integer attributeAsInt(Element root, String tag, String attr) {
        String v = attribute(root, tag, attr);
        if (v == null || v.isEmpty()) return null;
        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static Double attributeAsDouble(Element root, String tag, String attr) {
        String v = attribute(root, tag, attr);
        if (v == null || v.isEmpty()) return null;
        try {
            return Double.parseDouble(v);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}