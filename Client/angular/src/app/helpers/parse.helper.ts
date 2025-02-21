import katex from "katex";


export const parseKatex = (markedOutput: string) => {
  const blockRegex = /\$\$([\s\S]*?)\$\$/g;
  const inlineRegex = /\$([\s\S]*?)\$/g;
  markedOutput = markedOutput.replace(blockRegex, (_, equation) => {
    return katex.renderToString(equation, {displayMode: true});
  });

  markedOutput = markedOutput.replace(inlineRegex, (_, equation) => {
    return katex.renderToString(equation, {displayMode: false});
  });
  return markedOutput;
}
