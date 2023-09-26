/** @type {import('tailwindcss').Config} */
import withMT from '@material-tailwind/react/utils/withMT';
export default withMT({
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      spacing: {
        '280' : '70rem'
      },
      colors: {
        yesrae: {
          0: '#94E1FF',
          100: '#BCAAFF',
          200: '#9399FF',
        },
      },
    },
  },
  plugins: [],
});
