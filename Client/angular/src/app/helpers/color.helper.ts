export const toColor = (color: number) => {
  color >>>= 0;
  let b = color & 0xFF,
    g = (color & 0xFF00) >>> 8,
    r = (color & 0xFF0000) >>> 16,
    a = ((color & 0xFF000000) >>> 24) / 255;
  return "rgba(" + [r, g, b, a].join(",") + ")";
};
