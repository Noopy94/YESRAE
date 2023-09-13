/** @type {import('tailwindcss').Config} */
import withMT from '@material-tailwind/react/utils/withMT';
export default withMT({
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        yesrae: {
          0: '#AA57FF',
          100: '#646CFF',
          200: '#9399FF',
        },
      },
    },
  },
  plugins: [],
});
