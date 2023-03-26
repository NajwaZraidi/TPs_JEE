import socket
# Création d'un socket
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# Définition de l'adresse IP et du port du serveur
server_address = ('localhost', 1234) # Connexion au serveur
client_socket.connect(server_address) # Configuration du socket en mode Non Blocking
client_socket.setblocking(0) # Données à envoyer au serveur
message = 'Hello, server!' # Envoi des données au serveur
client_socket.send(message.encode()) # Récupération de la réponse du serveur
response = ''
while True:
    try:
        data = client_socket.recv(1024)
        if not data:
            break
        response += data.decode()
    except socket.error:
        pass

# Affichage de la réponse du serveur
print('Server response:', response)

# Fermeture du socket
client_socket.close()