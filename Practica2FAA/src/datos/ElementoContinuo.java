/**
 *
 * @author Daniel Garnacho y Diego Castaño
 */

package datos;

public class ElementoContinuo extends Elemento {
    private final double valor;
    public ElementoContinuo (double valor) {
       this.valor = valor; 
    }
    @Override
    public String getValorNominal() {
        return "";
    }
    @Override
    public double getValorContinuo() {
        return this.valor;
    }

    @Override
    public int hashCode() {
        return (new Double(valor)).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this.getValorContinuo() != ((ElementoContinuo)obj).getValorContinuo()) {
            return false;
        }
        return true;
    }

    @Override
    public TiposDeAtributos getTipo() {
        return TiposDeAtributos.Continuo;
    }
    
    public double diferencia(ElementoContinuo e) {
        return Math.pow(e.getValorContinuo() - this.getValorContinuo(), 2);
    }
} 
