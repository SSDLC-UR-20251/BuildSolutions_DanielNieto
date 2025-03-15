import sys
import random
import string

# TODO: Implementar la función para generar códigos de recuperación
def generar_codigo():
    codigo = ""
    caracteres = string.ascii_uppercase + string.ascii_lowercase + string.digits
    caracteres = caracteres.replace('0', '').replace('O', '').replace('1', '').replace('l', '')
    for i in range(6):
        codigo += random.choice(caracteres)
    return codigo

if __name__ == "__main__":
    if len(sys.argv) != 2 or not sys.argv[1].isdigit():
        print("Uso: python main.py <cantidad_de_codigos>")
        sys.exit(1)

    cantidad = int(sys.argv[1])
    print("Generando códigos de recuperación...")

    for i in range(cantidad):
        print(f"Código {i+1}: {generar_codigo()}")
