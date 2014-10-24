/**
 *
 * @author Daniel Garnacho y Diego Castaño
 */

package datos;

public class ElementoNominal extends Elemento {
    private final String valor;
    public ElementoNominal (String valor) {
        this.valor = valor;
    }
    
    @Override
    public String getValorNominal() {
        return this.valor;
    }
    @Override
    public double getValorContinuo() {
        return 0.0;
    }

    @Override
    public int hashCode() {
        return this.valor.hashCode();
    }
    
        @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.getValorNominal().equals( ((ElementoNominal)obj).getValorNominal() );
    }
    
    @Override
    public TiposDeAtributos getTipo() {
        return TiposDeAtributos.Nominal;
    }
    
    public double diferencia(ElementoNominal e) {
        if (e.getValorNominal().equals(this.getValorNominal())) {
            return 0.0;
        } else {
            return 1.0;
        }
    }
}