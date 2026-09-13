import asyncio
import websockets
import json
import ctypes
from ctypes import windll

# Windows API constants
MOUSEEVENTF_MOVE = 0x0001
MOUSEEVENTF_LEFTDOWN = 0x0002
MOUSEEVENTF_LEFTUP = 0x0004
MOUSEEVENTF_RIGHTDOWN = 0x0008
MOUSEEVENTF_RIGHTUP = 0x0010
MOUSEEVENTF_WHEEL = 0x0800

def move_mouse(dx, dy):
    ctypes.windll.user32.mouse_event(MOUSEEVENTF_MOVE, int(dx), int(dy), 0, 0)

def click(button='left'):
    if button == 'left':
        windll.user32.mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
        windll.user32.mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
    elif button == 'right':
        windll.user32.mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0)
        windll.user32.mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0)

def scroll(delta):
    windll.user32.mouse_event(MOUSEEVENTF_WHEEL, 0, 0, int(delta), 0)

def send_key(key):
    windll.user32.keybd_event(ord(key.upper()), 0, 0, 0)
    windll.user32.keybd_event(ord(key.upper()), 0, 2, 0)

async def handler(websocket):
    print(f"Client connected: {websocket.remote_address}")
    try:
        async for message in websocket:
            data = json.loads(message)
            action = data.get('action')
            
            if action == 'move':
                move_mouse(data['dx'], data['dy'])
            elif action == 'click':
                click(data.get('button', 'left'))
            elif action == 'scroll':
                scroll(data['delta'])
            elif action == 'key':
                send_key(data['key'])
    except websockets.exceptions.ConnectionClosed:
        print("Client disconnected")

async def main():
    print("Starting Remote Touchpad Server...")
    print("Server running on port 8765")
    print("Connect from Android app using: ws://<your-pc-ip>:8765")
    
    async with websockets.serve(handler, "0.0.0.0", 8765):
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())
