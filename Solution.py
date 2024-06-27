import base64

#The encrypted key
message='GlUaFBBTUkFARlJTQVRXRVdSFVVFQVRTWF5fBBMOFBYXFwgTRhcaFRZVWldXRl5JRhZWUV1BFQFO\nQUkQEFtdAgAMBRpSW1cUTVJOABBYXldFBB8MDwcXFwgTRgcHDRxTXFdXRl5JRgFRVVBaFQFOQUkQ\nEEFSBxdOTVMXUV1cRlJTQVRHXlwSRg8='

#Your Google username
key='arias0723'

decrypted_message=[]

#decode the key to base64 bytes
dec_bytes=base64.b64decode(message)

#XOR with Username
for a,b in enumerate(dec_bytes):
    decrypted_message.append(chr(b ^ ord(key[a%len(key)])))

#The encypted message
print("".join(decrypted_message))