# MixPlayer

![GitHub release (latest by date)](https://img.shields.io/github/v/release/v1tzor/MixPlayer)

Mobile App for playing audio and video files, as well as background listening to tracks using a special audio service. Multimodules, Compose, MVI, Clean Architecture;
For courses

Support:
- Theme: Dark, Light
- Languages: RU, EN
- Background listening audio
- Viewing videos

UI - Material Design 3

## Screenshots
<table>
  <tr>
    <td>Splash Screen</td>
    <td>Home Screen</td>
    <td>Details Screen</td>
  </tr>
  <tr>
    <td valign="top"><gif src="https://i.imgur.com/LpqDInD.gif" align="right" width="350dp"></td>
    <td valign="top"><img src="https://i.imgur.com/ZdOBE2B.gif" align="left" width="350dp"></td>
    <td valign="top"><img src="https://i.imgur.com/2ru0HUr.gif" align="right" width="350dp"></td>
  </tr>
  <tr>
    <td>Audio Screen</td>
    <td>Video Screen</td>
    <td>Settings Screen</td>
  </tr>
  <tr>
    <td valign="top"><img src="https://i.imgur.com/C910o0A.gif" align="left" width="350dp"></td>
    <td valign="top"><img src="https://i.imgur.com/qxiRmvD.gif" align="right" width="350dp"></td>
    <td valign="top"><img src="https://i.imgur.com/UJEg1sR.gif" align="left" width="350dp"></td>
  </tr>
 </table>
 
## Project structure
<p>
<img src="https://i.imgur.com/XvVbhbO.png" width="100%"></img>
</p>

## Player service

To communicate with the service , the following are used:
- MediaController – provides the transmission of media commands;
- PlaybackManager – provides transmission of the current state of the media player.

<p>
<img src="https://i.imgur.com/Rcs5UVH.png" width="100%"></img>
</p>

### License

```
Copyright 2023 Stanislav Aleshin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
