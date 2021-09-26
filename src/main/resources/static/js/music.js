const formatTime = (time) => {
    let min = Math.floor(time / 60);
    if (min < 10) {
        min = `0${min}`;
    }
    let sec = Math.floor(time % 60);
    if (sec < 10) {
        sec = `0${sec}`;
    }
    return `${min}:${sec}`;
}

function Sound(track) {
    this.sound = track;
    this.getDuration = () => formatTime(this.sound.duration());
    this.getCurrentTime = () => formatTime(this.sound.seek());
    this.setCurrentTime = (seconds) => this.sound.seek(seconds);
    this.getNotFormatedTime = () => this.sound.seek();
    this.getNotFormatedDuration = () => this.sound.duration();
    this.play = () => this.sound.play();
    this.pause = () => this.sound.pause();
    this.on = (event, fn) => this.sound.on(event, fn);
    this.unload = () => this.sound.unload();
}

function load(url) {
    const track = new Howl({
        src: [url],
        format: ["mp3"],
    });

    return new Sound(track);
}
