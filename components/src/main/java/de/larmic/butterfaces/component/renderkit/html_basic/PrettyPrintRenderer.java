package de.larmic.butterfaces.component.renderkit.html_basic;

import de.larmic.butterfaces.component.html.HtmlPrettyPrint;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

/**
 * Created by larmic on 31.07.14.
 */
@FacesRenderer(componentFamily = HtmlPrettyPrint.COMPONENT_FAMILY, rendererType = HtmlPrettyPrint.RENDERER_TYPE)
public class PrettyPrintRenderer extends com.sun.faces.renderkit.html_basic.HtmlBasicRenderer {

    public static final String ELEMENT_DIV = "div";
    public static final String ELEMENT_PRE = "pre";
    public static final String ATTRIBUTE_STYLE = "style";
    public static final String ATTRIBUTE_CLASS = "class";

    @Override
    public void encodeBegin(final FacesContext context, final UIComponent component) throws IOException {
        rendererParamsNotNull(context, component);

        if (!shouldEncode(component)) {
            return;
        }

        final ResponseWriter writer = context.getResponseWriter();
        final HtmlPrettyPrint prettyPrint = (HtmlPrettyPrint) component;

        final String style = prettyPrint.getStyle();
        final String styleClass = prettyPrint.getStyleClass();

        writer.startElement(ELEMENT_DIV, component);

        writer.writeAttribute("id", component.getClientId(), null);

        if (null != style) {
            writer.writeAttribute(ATTRIBUTE_STYLE, style, "style");
        }
        if (null != styleClass) {
            writer.writeAttribute(ATTRIBUTE_CLASS, "butter-component-prettyprint " + styleClass, "class");
        } else {
            writer.writeAttribute(ATTRIBUTE_CLASS, "butter-component-prettyprint", "class");
        }

        writer.startElement(ELEMENT_PRE, component);
        writer.writeAttribute(ATTRIBUTE_CLASS, "prettyprint " + prettyPrint.getLanguage(), "class");
    }

    @Override
    public void encodeEnd(final FacesContext context, final UIComponent component) throws IOException {
        rendererParamsNotNull(context, component);

        if (!shouldEncode(component)) {
            return;
        }

        final ResponseWriter writer = context.getResponseWriter();
        final HtmlPrettyPrint prettyPrint = (HtmlPrettyPrint) component;

        writer.endElement(ELEMENT_PRE);
        writer.endElement(ELEMENT_DIV);

        // call pretty print javascript on render
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", "type");
        writer.writeText("jQuery(function () { handlePrettyPrint('" + component.getClientId() + "'); });", null);
        writer.endElement("script");
    }

}
