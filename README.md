#YTDL-Frontend
A frontend for the youtube-dl script.

<b>Disclaimer:</b>
Any contributors to this repository (we) are not responsible for any actions taken against you. While no warranty is provided and our license does not allow holding us liable for damages, please also realize that this code performs no actions relating to downloading content or accessing anything on the internet. It simply simplifies the process of running another piece of software, created by other people. <b>WE ARE NOT RESPONSIBLE FOR YOUR ACTIONS!!!</b>

#Downloading
All versions of YTDL-Frontend are available in the comp folder, all you need is the .jar. The latest version is 0a. As for youtube-dl itself, please check out their <a href="https://rg3.github.io/youtube-dl/">website</a>.

#Use
The software should be pretty self explanatory... (Just paste the URL and press enter!)

To work, youtube-dl must be on the PATH, installed, etc... Basically accessible with just it's name from the command line, without it's directory (folder). If it is not or you don't feel like bothering, a custom command start can be specified in the Settings tab. It should work fine if you used the Debian installer, for Windows you'll need something like:

C:\Users\<UserName>\AppData\Local\Programs\Python\<PythonVersion>\python C:\Users\<UserName>\youtube-dl

However, without more cmd wizardry than I know (or care about), the paths cannot have spaces in them. I ended up getting it working by putting necessary files in the Public Windows account.

#Contributors
Most recent generated javadoc is available for each version in the comp folder. The project also requires my deb-lib.
